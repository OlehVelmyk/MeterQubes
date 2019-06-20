process
    .on('unhandledRejection', (reason, p) => {
        console.error('Reason: ', reason);
        console.error('Unhandled Rejection at Promise: ', p);
    })
    .on('uncaughtException', err => {
        console.error('Uncaught Exception thrown: ', err);
        process.exit(1);
    });

const Web3 = require('web3');
const web3 = new Web3(new Web3.providers.HttpProvider('https://ropsten.infura.io/v3/f6bdecf6722f4a3faa2abec1562e01ed'));
const crypto = require('crypto');
const BigNumber = require('bignumber.js');
const fs = require('fs');
// fromRpcSig, ecrecover, hashPersonalMessage, toBuffer, pubToAddress
const {keccak256, ecsign} = require('ethereumjs-util');

const gethSigPrefix = "\x19Ethereum Signed Message:\n32";


const sha3ToHex = message => {
    return '0x' + keccak256(message).toString('hex')
};


const EIP712_DOMAIN_TYPEHASH = sha3ToHex('EIP712Domain(string name)');


const EIP712_ORDER_TYPE = sha3ToHex(
    'Order(address trader,address relayer,address baseToken,address quoteToken,uint256 baseTokenAmount,uint256 quoteTokenAmount,uint256 gasTokenAmount,bytes32 data)'
);


const generateOrderData = order => {
    let res = '0x'
    res += addLeadingZero(new BigNumber(order.version).toString(16), 2);
    res += order.isSell ? '01' : '00';
    res += order.isMarket ? '01' : '00';
    res += addLeadingZero(new BigNumber(order.expiredAtSeconds).toString(16), 5 * 2);
    res += addLeadingZero(new BigNumber(order.asMakerFeeRate).toString(16), 2 * 2);
    res += addLeadingZero(new BigNumber(order.asTakerFeeRate).toString(16), 2 * 2);
    res += addLeadingZero(new BigNumber(order.makerRebateRate).toString(16), 2 * 2);
    res += addLeadingZero(new BigNumber(order.salt).toString(16), 8 * 2);

    return addTailingZero(res, 66)
};


function addLeadingZero(str, length) {
    let len = str.length;
    return '0'.repeat(length - len) + str
}


function addTailingZero(str, length) {
    let len = str.length;
    return str + '0'.repeat(length - len)
}

function padToBytes32(n) {
    while (n.length < 64) n += "0";
    return "0x" + n;
}


const getOrderHash = (order) => {
    return getEIP712MessageHash(
        sha3ToHex(
            EIP712_ORDER_TYPE +
            addLeadingZero(order.trader.slice(2), 64) +
            addLeadingZero(order.relayer.slice(2), 64) +
            addLeadingZero(order.baseToken.slice(2), 64) +
            addLeadingZero(order.quoteToken.slice(2), 64) +
            addLeadingZero(new BigNumber(order.baseTokenAmount).toString(16), 64) +
            addLeadingZero(new BigNumber(order.quoteTokenAmount).toString(16), 64) +
            addLeadingZero(new BigNumber(order.gasTokenAmount).toString(16), 64) +
            order.data.slice(2)
        )
    )
};

function getEIP712MessageHash(message) {
    return sha3ToHex('0x1901' + getDomainSeparator().slice(2) + message.slice(2), {
        encoding: 'hex'
    })
}


function getDomainSeparator() {
    return sha3ToHex(
        EIP712_DOMAIN_TYPEHASH +
        sha3ToHex('Hydro Protocol').slice(2)
    )
}

function ethSign(data, pk) {
    const hash = web3.utils.soliditySha3(gethSigPrefix, data);
    let {v, r, s} = ecsign(Buffer.from(hash.slice(2), 'hex'), pk);
    return {v, r: '0x' + r.toString('hex'), s: '0x' + s.toString('hex')}
}

const exchangeAddress = "0x112863ff5087c5542542b5ab47c46af3dd97b153";
const exchangeABI = require('./exchangeABI');
const exchangeContract = new web3.eth.Contract(exchangeABI, exchangeAddress);

const relayer = '0x37398a06d8bd553410d7404680ebed4638bb005b';
const taker = '0x7683ddb7451d684d320baf7aabcd0298a8c941da'; // EMPR seller
const takerPK = Buffer.from('468ED419D21323B5C94EDA98C5AF6AB065265674E71D0B94ED885F271588EC6D', 'hex');
const maker = '0x96241186ca4aa79f5bd593a02c29a38da785251e' ;// EMPR buyer
const makerPK = Buffer.from('D96315659BA73E284B3F7C0B4298945F083A9EF3292D9DDF5D2B92B6174C0D44', 'hex');
const baseToken = '0xdaf16fad57bc1d43a57695d1e724013ebe8e154a'; // EMPR token
const quoteToken = '0x251bbfa0abf2dc356a44f7af5e4d7a224a4ec01f' ;// WETH token

const baseDecimals = 18;
const quoteDecimals = 18;

const priceBuy = 1;
const priceSell = 0.5;
const takerAmount = 0.001;
const makerAmount = 0.001;

const gasPriceAvg = 10 / Math.pow(10, 9) ;// Gwei to Wei
const gasAmount = gasPriceAvg * 190000;

const takerOrder = {
    version: 2, // uint256 public constant SUPPORTED_ORDER_VERSION = 2
    trader: taker,
    relayer,
    baseToken,
    quoteToken,
    baseTokenAmount: takerAmount * Math.pow(10, baseDecimals),
    quoteTokenAmount: takerAmount * priceSell * Math.pow(10, quoteDecimals),
    gasTokenAmount: gasAmount * takerAmount * Math.pow(10, baseDecimals),
    isSell: true,
    isMarket: false,
    expiredAtSeconds: 1584791531, // expires in a year: Math.floor(Date.now()/1000) + 60*60*24*365
    asMakerFeeRate: 100,  // uint256 public constant FEE_RATE_BASE = 100000
    asTakerFeeRate: 100,  // uint256 public constant FEE_RATE_BASE = 100000
    makerRebateRate: 0,   // uint256 public constant REBATE_RATE_BASE = 100
    salt: parseInt(crypto.randomBytes(6).toString('hex'), 16) // 8 bytes available in order.data, but JS handles only 53 bits (< 7 bytes)
};

console.log('takerOrder: ', takerOrder)
fs.writeFileSync('/home/ubuntu/Intellij Idea/com.scenario_projects.mq_back_stage/src/test/java/resources/exchangeOrderData.json', JSON.stringify(takerOrder))

const makerOrder = {
    version: 2, // uint256 public constant SUPPORTED_ORDER_VERSION = 2
    trader: maker,
    relayer,
    baseToken,
    quoteToken,
    baseTokenAmount: makerAmount * Math.pow(10, baseDecimals),
    quoteTokenAmount: makerAmount * priceBuy * Math.pow(10, quoteDecimals),
    gasTokenAmount: gasAmount * makerAmount * Math.pow(10, baseDecimals),
    isSell: false,
    isMarket: false,
    expiredAtSeconds: 1584791531, // expires in a year: Math.floor(Date.now()/1000) + 60*60*24*365
    asMakerFeeRate: 100,  // uint256 public constant FEE_RATE_BASE = 100000
    asTakerFeeRate: 100,  // uint256 public constant FEE_RATE_BASE = 100000
    makerRebateRate: 0,   // uint256 public constant FEE_RATE_BASE = 100
    salt: parseInt(crypto.randomBytes(6).toString('hex'), 16) // 8 bytes available in order.data, but JS handles only 53 bits (< 7 bytes)
};


takerOrder.data = generateOrderData(takerOrder);
makerOrder.data = generateOrderData(makerOrder);

const takerOrderId = getOrderHash(takerOrder);
const makerOrderId = getOrderHash(makerOrder);

const takerOrderSigVRS = ethSign(takerOrderId, takerPK);
let takerOrderSigConfig = Number(takerOrderSigVRS.v).toString(16) + '00' ;// 00 - for EthSign (01 - for EIP712)
takerOrderSigConfig = padToBytes32(takerOrderSigConfig);
const struct_takerOrderSig = [takerOrderSigConfig, takerOrderSigVRS.r, takerOrderSigVRS.s];

const makerOrderSigVRS = ethSign(makerOrderId, makerPK);
let makerOrderSigConfig = Number(makerOrderSigVRS.v).toString(16) + '00'; // 01 - for EIP712 (00 - for EthSign)
makerOrderSigConfig = padToBytes32(makerOrderSigConfig);
const struct_makerOrderSig = [makerOrderSigConfig, makerOrderSigVRS.r, makerOrderSigVRS.s];


// **********************************************

const takerOrderParam = [
    takerOrder.trader,
    takerOrder.baseTokenAmount.toString(),
    takerOrder.quoteTokenAmount.toString(),
    takerOrder.gasTokenAmount.toString(),
    takerOrder.data,
    struct_takerOrderSig
];

const makerOrderParams = [[
    makerOrder.trader,
    makerOrder.baseTokenAmount.toString(),
    makerOrder.quoteTokenAmount.toString(),
    makerOrder.gasTokenAmount.toString(),
    makerOrder.data,
    struct_makerOrderSig
]];

const baseTokenFilledAmounts = [makerOrder.baseTokenAmount];

const orderAddressSet = [baseToken, quoteToken, relayer];


console.log('takerOrderParam: ', takerOrderParam);
console.log('makerOrderParams: ', makerOrderParams);
console.log('orderAddressSet: ', orderAddressSet);

const Tx = require('ethereumjs-tx');
const privateKey = Buffer.from('2BB7104B5733DF81C0053071C8726A80CECBD51C54792EE5190706C504F9DDFD', 'hex'); // relayer


web3.eth.getTransactionCount(relayer)
    .then(txCount => {

        // construct the transaction data
        const txData = {
            nonce: web3.utils.toHex(txCount),
            gasLimit: web3.utils.toHex(300000), // 190000 ?
            gasPrice: web3.utils.toHex(10e9),    // Gwei
            from: relayer,
            to: exchangeAddress,
            data: exchangeContract.methods.matchOrders(takerOrderParam, makerOrderParams, baseTokenFilledAmounts, orderAddressSet).encodeABI()
            //value: web3.utils.toHex(web3.utils.toWei(123, 'wei'))
        };
        return txData;
    })
    .then(txData => {
        console.time('ready to send');
        sendSigned(txData, (err, result) => {
            if (err) return console.error('got error: ', err);
            console.log('Tx sent! Got transaction hash: ', result)
            fs.writeFileSync('src/test/java/resources/transactionHash.txt', result);
            //fs.writeFileSync('src\\test\\java\\resources\\transactionHash.txt', result);
        });
        console.timeEnd('ready to send')
    });

function sendSigned(txData, cb) {
    const transaction = new Tx(txData);
    transaction.sign(privateKey);
    const serializedTx = transaction.serialize().toString('hex');
    web3.eth.sendSignedTransaction('0x' + serializedTx, cb)
}



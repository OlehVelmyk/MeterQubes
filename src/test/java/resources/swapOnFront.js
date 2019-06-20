/* 
const Web3 = require('web3')
if (web3.currentProvider.isMetamask) const web3 = new Web3(web3.currentProvider)
else console.error('MetaMask not found!')
 */

const Web3 = require('web3');
const web3 = new Web3(new Web3.providers.HttpProvider('https://ropsten.infura.io/v3/f6bdecf6722f4a3faa2abec1562e01ed'));

const {ecrecover, toBuffer, pubToAddress} = require('ethereumjs-util');
const BigNumber = require('bignumber.js');
const fs = require('fs');

//************************************************ */
// METHODS and CONSTANTS
//************************************************ */

const hydroSwapABI = [{
    "constant": false,
    "inputs": [{
        "name": "id",
        "type": "bytes32"
    },
        {
            "name": "orderAddresses",
            "type": "address[5]"
        },
        {
            "name": "orderValues",
            "type": "uint256[6]"
        },
        {
            "name": "v",
            "type": "uint8"
        },
        {
            "name": "r",
            "type": "bytes32"
        },
        {
            "name": "s",
            "type": "bytes32"
        }],
    "name": "swap",
    "outputs": [{
        "name": "takerTokenFilledAmount",
        "type": "uint256"
    }],
    "payable": true,
    "stateMutability": "payable",
    "type": "function"
}]

const gethSigPrefix = "\x19Ethereum Signed Message:\n32"

const getExchangeOrderHash = (order) => {
    return web3.utils.soliditySha3(
        order.exchange,
        order.maker,
        order.taker,
        order.makerToken,
        order.takerToken,
        order.feeRecipient,
        order.makerTokenAmount,
        order.takerTokenAmount,
        order.makerFee,
        order.takerFee,
        order.expirationTimestampInSec,
        order.salt
    )
}

const isValidSignature = (account, signature, message) => {
    const pubkey = ecrecover(toBuffer(message), v, toBuffer(signature.r), toBuffer(signature.s))
    const address = '0x' + pubToAddress(pubkey).toString('hex')
    return address.toLowerCase() === account.toLowerCase()
}

//************************************************ */    
// INPUT
//************************************************ */

// got as response from POST /exchange/

let exchangeOrderId_DB
let exchangeOrderId_Eth = '0x018c87aac32ec7f7c217351f455d17c9dcc4616b90800b3bba6e478f730d92bc'
let exchangeOrderData = {
    exchange: '0x4aa335be177bba2fe3289ceb94d00fb34e94007b',
    maker: '0x4EC7e7530e247aa3343Aa7Bc17e16779A5758e5c',
    taker: '0xB4b0145fe5A020D855E827A0630500f2A849dADE',
    makerToken: '0xdaf16fad57bc1d43a57695d1e724013ebe8e154a',
    takerToken: '0x251bbfa0abf2dc356a44f7af5e4d7a224a4ec01f',
    feeRecipient: '0x37398a06d8bd553410d7404680ebed4638bb005b',
    makerTokenAmount: new BigNumber(200000000000000000), //new BigNumber(200000000000000000)
    takerTokenAmount: new BigNumber(100000000000000000), //new BigNumber(100000000000000000)
    makerFee: 0,
    takerFee: 0,
    expirationTimestampInSec: 1556544540,
    salt: 238807586794061
}

let exchangeOrderSignature = {
    v: 28,
    r:
        '0xec0dc13a006659cc5d515577386f831d810cf84aba37fe6214e250deef1f7ca0',
    s:
        '0x1b6c0219437a80d346f165014f98527a4d9a5cceb5e499d04394249a7059632f'
}

console.log('exchangeOrderData: ', exchangeOrderData)
fs.writeFileSync('/home/ubuntu/Intellij Idea/com.scenario_projects.mq_back_stage/src/test/java/resources/exchangeOrderData.json', JSON.stringify(exchangeOrderData));

//************************************************ */
// OPERATION
//************************************************ */

/* 
const exchangeOrderHash = getExchangeOrderHash(exchangeOrderData)
const exchangeOrderToSign = web3.utils.soliditySha3(gethSigPrefix, exchangeOrderHash)

const signatureValidated = isValidSignature(exchangeOrderData.maker, exchangeOrderSignature, exchangeOrderToSign)
if(!signatureValidated) return console.error('Invalid maker_s signature')
 */

let orderAddresses = [
    exchangeOrderData.maker,
    exchangeOrderData.taker,
    exchangeOrderData.makerToken,
    exchangeOrderData.takerToken,
    exchangeOrderData.feeRecipient
]

let orderValues = [
    exchangeOrderData.makerTokenAmount.toString(),
    exchangeOrderData.takerTokenAmount.toString(),
    exchangeOrderData.makerFee.toString(),
    exchangeOrderData.takerFee.toString(),
    exchangeOrderData.expirationTimestampInSec.toString(),
    exchangeOrderData.salt.toString()
]

let {v, r, s} = exchangeOrderSignature
/*
// works but deprecated
const HydroSwapInstance = web3.eth.contract(hydroSwapABI).at(exchangeOrderData.taker)


// tx hash returned - submit to BE along with exchangeOrderId_DB
// if not ETH - approve tokens first, then call SC without value
HydroSwapInstance.swap(exchangeOrderId_Eth, orderAddresses, orderValues, v, r, s, { 
    from: web3.eth.coinbase, 
    value: exchangeOrderData.takerTokenAmount.toString(), 
    gas: 220000, 
    gasPrice: 5000000000    // get standard price from gas API
}, (err, txHash) => {
    if (err) console.error(err)
    else console.log(txHash)
})
*/

const HydroSwapContract = new web3.eth.Contract(hydroSwapABI, exchangeOrderData.taker)
let tx_data = HydroSwapContract.methods.swap(exchangeOrderId_Eth, orderAddresses, orderValues, v, r, s).encodeABI()
tx_data = web3.utils.toHex(tx_data)
// console.log('tx_data: ', tx_data)

/*
let transactionParameters = {
  gasPrice: new BigNumber(5000000000),    // get standard price from gasprice API
  gasLimit: '220000',
  to: exchangeOrderData.taker,
  from: ethereum.selectedAddress,
  value: exchangeOrderData.takerTokenAmount.toString(16), // ONLY IF user gives ETH to buy tokens
  data: tx_data // e.g. '0x62c1f38928ea4092318ca480f7247077f283d915e70eb5a7367595e78a22d40386c696a900000000000000000000000096241186ca4aa79f5bd593a02c29a38da785251e000000000000000000000000619e403af18365d09719456923c4a93b41ad3db1000000000000000000000000daf16fad57bc1d43a57695d1e724013ebe8e154a000000000000000000000000251bbfa0abf2dc356a44f7af5e4d7a224a4ec01f00000000000000000000000037398a06d8bd553410d7404680ebed4638bb005b000000000000000000000000000000000000000000000000002386f26fc10000000000000000000000000000000000000000000000000000002386f26fc1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000005cbdc2860000000000000000000000000000000000000000000000000000cbf04ef177cf000000000000000000000000000000000000000000000000000000000000001b4d4f6dcd55ae4e472b76e91bf917d3c9ef61343b08115cd09545c510d0a2202c5f782ae6cdbd82fae4dc1020cde905ed662043c9d35f6dd73498f8d9eb0ebc89',
}

ethereum.sendAsync({
  method: 'eth_sendTransaction',
  params: [transactionParameters],
  from: ethereum.selectedAddress,
}, (err, result) => {
    if (err) console.error(err)
    else {
        console.log(result.result)
    }
})
*/

// 0xec02cd665bbc67e98cd44d2a01995b0593df64a77dc63a16117b54e03b9ecfba

//const gethSigPrefix = "\x19Ethereum Signed Message:\n32"
const relayer = '0x37398a06d8bd553410d7404680ebed4638bb005b'; //---------------------------------------------------------/-------------------/------------
const maker = '0x4EC7e7530e247aa3343Aa7Bc17e16779A5758e5c' // EMPR buyer  was   0x96241186ca4aa79f5bd593a02c29a38da785251e
const makerPK = Buffer.from('D96315659BA73E284B3F7C0B4298945F083A9EF3292D9DDF5D2B92B6174C0D44', 'hex')

const Tx = require('ethereumjs-tx')


web3.eth.getTransactionCount(relayer)
    .then(txCount => {

        // construct the transaction data
        const txData = {
            nonce: web3.utils.toHex(txCount),
            gasLimit: web3.utils.toHex(300000), // 190000 ?
            gasPrice: web3.utils.toHex(10e9),    // Gwei
            from: maker,
            to: exchangeOrderData.taker,
            data: tx_data
            //value: web3.utils.toHex(web3.utils.toWei(123, 'wei'))
        }
        return txData;
    })
    .then(txData => {
        console.time('ready to send')
        sendSigned(txData, (err, result) => {
            if (err) return console.error('Got recipe: ', err)
            console.log('Tx sent! Got transaction hash: ', result)
            fs.writeFileSync('src/test/java/resources/transactionHash.txt', result);
        })
        console.timeEnd('ready to send')
    })

function sendSigned(txData, cb) {
    const transaction = new Tx(txData)
    transaction.sign(makerPK)
    const serializedTx = transaction.serialize().toString('hex')
    web3.eth.sendSignedTransaction('0x' + serializedTx, cb)
}
const ethUtil = require('ethereumjs-util');
const sigUtil = require('eth-sig-util');
const fs = require('fs');

// user's wallet (publicAddress)
const publicAddress = '0x4EC7e7530e247aa3343Aa7Bc17e16779A5758e5c';

// private key of user's publicAddress (MetaMask -> Account Options -> Детали счета -> Экспортировать закрытый ключ)
const pk = Buffer.from('766E216E69240E53BBE86F83D322DAD8ADD274334605AC1055D7BC21F30FF312', 'hex');

// user's nonce
const nonce = fs.readFileSync("/home/ubuntu/Intellij Idea/com.scenario_projects.mq_back_stage/src/test/java/resources/nonce.txt", "utf8");
//const nonce = fs.readFileSync("src\\test\\java\\resources\\nonce.txt", "utf8");
console.log('Nonce: ', nonce);

// authentication message
const msg = 'MeterQubes authentication with one-time nonce: ' + nonce;
const msgHex = ethUtil.bufferToHex(Buffer.from(msg, 'utf8'));
const signature = sigUtil.personalSign(pk, {data: msgHex});
const address = sigUtil.recoverPersonalSignature({data: msgHex, sig: signature});
// address should be == publicAddress

console.log('Address: ', address);
console.log('publicAddress: ', publicAddress);
console.log('signature: ', signature);

fs.writeFileSync('/home/ubuntu/Intellij Idea/com.scenario_projects.mq_back_stage/src/test/java/resources/signature.txt', signature);
//fs.writeFileSync('src\\test\\java\\resources\\signature.txt', signature);
const express = require('express');
const creatError = require('http-errors');
const router = express.Router();

module.exports = (app) => {
    var wallet = require("../controller/controller.wallet.js")

    app.post('/insertWalletData', wallet.insertWalletData)
    app.post('/getWalletData', wallet.getWalletData)

    
}
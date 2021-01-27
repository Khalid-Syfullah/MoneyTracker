var mongoose = require("mongoose")

var walletSchema = new mongoose.Schema({
    title: {type: String},
    type: {type: String},
    currency: {type: String},
    balance: {type: String}
})

var Wallet = mongoose.model("wallet",walletSchema)
module.exports = Wallet
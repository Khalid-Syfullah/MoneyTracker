var mongoose = require("mongoose")

var transactionSchema = new mongoose.Schema({
    title: {type: String, required: true},
    category: {type: String, required: true},
    wallet: {type: String, required: true},
    date: {type: String, required: true},
    amount: {type: String, required: true},
    transaction: {type: String, required: true}

})

var Transaction = mongoose.model("transaction",transactionSchema)
module.exports = Transaction
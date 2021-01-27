var mongoose = require("mongoose")

var goalSchema = new mongoose.Schema({
    title: {type: String},
    amount: {type: String},
    currency: {type: String},
    date: {type: String}
})

var Goal = mongoose.model("goal",goalSchema)
module.exports = Goal
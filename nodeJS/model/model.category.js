var mongoose = require("mongoose")

var categorySchema = new mongoose.Schema({
    title: {type: String}
})

var Category = mongoose.model("category",categorySchema)
module.exports = Category
var Transaction = require("../model/model.transaction.js")

exports.insertTransactionData = function(req,res){
    var transaction = new Transaction();
    transaction.title = req.body.title;
    transaction.amount = req.body.amount;
    transaction.transaction = req.body.transaction;
    transaction.category = req.body.category;
    transaction.wallet = req.body.wallet;
    transaction.date = req.body.date;
    

    transaction.save()
    .then(function(data){
        console.log("Transaction inserted")
        console.log(data)
        res.send({message: "Transaction successful"})
    })
    .catch(function(err){
        console.log("Transaction failed")
        res.send({message: "Transaction failed"})
    })
}

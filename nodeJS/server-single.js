var express = require("express")
var app = express()
var mongoose = require("mongoose")
var bodyParser = require("body-parser")


var userSchema = new mongoose.Schema({
    name: {type: String, required: true},
    email: {type: String, required: true},
    pass: {type: String, required: true}
})

var overviewSchema = new mongoose.Schema({
    spent: {type: Number, required: true},
    remaining: {type: Number, required: true},
    limit: {type: Number, required: true}
})

var transactionSchema = new mongoose.Schema({
    email: {type: String, required: true},
    title: {type: String, required: true},
    category: {type: Number, required: true},
    wallet: {type: String, required: true},
    date: {type: String, required: true},
    amount: {type: Number, required: true},
    transaction: {type: String, required: true}

})


var walletSchema = new mongoose.Schema({
    title: {type: String, required: true},
    type: {type: String, required: true},
    currency: {type: String, required: true},
    balance: {type: Number, required: true},
    email:{type:String, required: true}
})


var goalSchema = new mongoose.Schema({
    title: {type: String, required: true},
    targetAmount: {type: Number, required: true},
    wallet: {type: String, required: true},
    currency: {type: String,required: true},
    date: {type: String, required: true},
  
    email: {type: String, required: true}
})

var categorySchema = new mongoose.Schema({
    title: {type: String, required: true},
    email: {type: String, required: true},
    iconId:{type: Number,required:true}
})


var User = mongoose.model("user",userSchema)
var Overview = mongoose.model("overview",overviewSchema)
var Transaction = mongoose.model("transaction",transactionSchema)
var Wallet = mongoose.model("wallet",walletSchema)
var Goal = mongoose.model("goal",goalSchema)
var Category = mongoose.model("category",categorySchema)

var url = "mongodb://localhost/moneytracker"

mongoose.connect(url, function(err){
    if(err){
		console.log("Unable to connect to database: " + err)
    }
    else{
		console.log("Connected to database!")
    }
})

app.use(bodyParser.urlencoded({extended: true}))
app.use(bodyParser.json())
app.listen(8800, function(){
    console.log("Listening to port 8800")
})



app.post('/api/login', function(req,res){
    var user = new User();
    user.email = req.body.email;
    user.pass = req.body.pass;
    


    User.find({email:user.email, pass:user.pass},(err,results)=>{

        if(err){
            console.log(err)
            return
        }
        else{
            if(results.length==0){
                res.send({serverMsg: "not found"})
                return
            }
            else{
                var response={'name' : results[0].name,'email':results[0].email,'pass':results[0].pass,'serverMsg':'successful'}
                res.send(response)
                
                console.log(response)
                return
            }
        }

    })
    // .then(function(user){
    //     console.log(user)
    //     res.send({serverMsg: "successful"})


    // })
    // .catch(function(err){
    //     console.log(err)
    // })
})


app.post('/api/signup', function(req,res){
    var user = new User();
    user.name = req.body.name;
    user.email = req.body.email;
    user.pass = req.body.pass;

    User.find({email:user.email},(err,results)=>{
        if(err){
            console.log(err)
        }
        else{
            if(results.length>0){
                res.send({ serverMsg: "exists" });
                return
            }
            else{
                user.save().then(data=>{
                    console.log(data)
                    res.send({serverMsg: "Registration successful"})
                }).catch((err)=>{
                    console.log(err)
                    res.send({serverMsg: "Failed"})
                })

            }
        }
    })


    // user.save()
    // .then(function(user){
    //     console.log(user)
    //     res.send({message: "Signup successful"})

    // })
    // .catch(function(err){
    //     console.log(err)
    // })
})

app.post('/api/insertTransactionData', function(req,res){
    var transaction = new Transaction();
    transaction.email = req.body.email;
    transaction.title = req.body.title;
    transaction.amount = req.body.amount;
    transaction.transaction = req.body.transaction;
    transaction.category = req.body.category;
    transaction.wallet = req.body.wallet;
    transaction.date = req.body.date;
    var newbalance=0;
    var updateQuery;

    Wallet.find({email:req.body.email,title: req.body.wallet}).then((data)=>{
        newbalance=data[0].balance
        console.log(data)
        console.log(data[0].balance,newbalance)
        if(transaction.transaction=="Income"){
            updateQuery={$set: {balance: data[0].balance + transaction.amount}}
              // newbalance=newbalance+ transaction.amount
      
          }
          else{
            
              updateQuery={$set: {balance: data[0].balance - transaction.amount}}
          }

        Wallet.updateOne({email: req.body.email,title:req.body.wallet}, updateQuery)
        .then(function(results){
            console.log("Wallet updated")
            console.log(results)

            transaction.save()
            .then(function(data){
                console.log("Transaction inserted")
                console.log(data)
                
                
               
                res.send({serverMsg: "Transaction successful"})
        
        
            })
            .catch(function(err){
                console.log(req.body)
                res.send({serverMsg: "Transaction failed"})
            })

            
            
        })
        .catch(function(err){
            console.log(err)
            console.log("Wallet update failed")
            
        })
    }).catch((err)=>{
        console.log(err)
    })
   

   
   
  
   

  
})


app.post('/api/insertWalletData', function(req,res){
    var wallet = new Wallet();
    
    wallet.title = req.body.title;
    wallet.type = req.body.type;
    wallet.currency = req.body.currency;
    wallet.email=req.body.email
    wallet.balance = "0";

    Wallet.find({email:wallet.email,title:wallet.title},(err,results)=>{
        if(err){
            console.log(err)
            res.send({serverMsg:'error occured'})
            return
        }
        else{
            if(results.length>0){
                res.send({serverMsg: 'wallet exists'})
                return
            }
            else{
                wallet.save()
                .then(function(data){
                    console.log("Wallet inserted")
                    console.log(data)
                    res.send({serverMsg: "Wallet inserted"})
                })
                .catch(function(err){
                    console.log("Wallet insertion failed")
                    res.send({serverMsg: "Wallet insertion failed"})
                })
            }
        }
    })

  
})

app.post('/api/insertGoalData', function(req,res){
    var goal = new Goal();
    
    goal.title = req.body.title
    goal.targetAmount = req.body.targetAmount
    goal.wallet = req.body.wallet
    goal.currency = req.body.currency
    goal.date = req.body.date
    // goal.progress = req.body.acquiredAmount / req.body.targetAmount * 100
    goal.email = req.body.email

    goal.save()
    .then(function(data){
        console.log("Goal inserted")
        console.log(data)
        res.send({serverMsg: "Goal inserted"})
    })
    .catch(function(err){
        console.log("Goal insertion failed")
        res.send({serverMsg: "Goal insertion failed"})
    })
})



app.post('/api/insertCategoryData', function(req,res){
    var category = new Category();
    
    category.title = req.body.title
    category.email = req.body.email
    category.iconId=req.body.iconId
    

    category.save()
    .then(function(data){
        console.log("Category inserted")
        console.log(data)
        res.send({serverMsg: "Category inserted"})
    })
    .catch(function(err){
        console.log("Category insertion failed")
        res.send({serverMsg: "Category insertion failed"})
    })
})




app.post('/api/getOverviewData', function(req,res){

    var overview = new Overview();
    overview.spent = 0;
    overview.remaining = 0;
    overview.limit = 0;

    Transaction.find({email:req.body.email})
    .then(function(results){

        if(results.length>0){
           

            console.log("Overview found")
            console.log(overview)

            for(result of results){

                overview.spent += result.amount
                overview.remaining += result.amount
                overview.limit += result.amount

                console.log("Transactions :")
                console.log(result)
            }



        }

        res.send(overview)

    })
    .catch(function(err){
        res.send({serverMsg: "Response error"})

    })
    
    
})


app.post('/api/getTransactionData', function(req,res){

    Transaction.find({email: req.body.email})
    .then(function(data){
        console.log("Transaction found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
})

app.post('/api/getWalletData', function(req,res){


    Wallet.find({email:req.body.email})
    .then(function(data){
        console.log("Wallets found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
})



app.post('/api/getGoalData', function(req,res){


    Goal.find({email:req.body.email})
    .then(function(data){
        console.log("Goals found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
})



app.post('/api/getCategoryData', function(req,res){


    Category.find({email:req.body.email})
    .then(function(data){
        console.log("Category found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({serverMsg: "Error found"})
    })
})



app.post('/api/getGraphOverviewData', async function(req,res){

    var dailySpendingAmount = []
    var weeklySpendingAmount = []
    var monthlySpendingAmount = []
    var currentDate=0, sum=0
    var i = 0
    
    if(req.body.timeline == "daily"){
        console.log("START DAILY")

        for(var date of req.body.dailyList){
            await Transaction.find({email:req.body.email, date: date})
            .then(function(results){
    
    
                for(result of results){
                    if(result.transaction=="Expense"){
                        sum += result.amount
    
                    }
                }
    
                if(sum != 0){
                    dailySpendingAmount.push(sum)
                    sum = 0
                }
                else{
                    dailySpendingAmount.push(0)
                }
            })
        }
    
            console.log("Daily Spending:")
            console.log({
                dailyOverviewSpendingAmount:dailySpendingAmount,
                dailyOverviewSpendingDate:req.body.dailyList})
                    
            res.send({
                dailyOverviewSpendingAmount:dailySpendingAmount,
                dailyOverviewSpendingDate:req.body.dailyList})
    
    }

    if(req.body.timeline == "weekly"){
        console.log("START WEEKLY")
        for(var date of req.body.weeklyList){
            await Transaction.find({email:req.body.email, date: date})
            .then(function(results){
    
    
                for(result of results){
                    if(result.transaction=="Expense"){
                        sum += result.amount
                    }
                }
                if(sum != 0){
                    weeklySpendingAmount.push(sum)
                    sum = 0
                }
                else{
                    weeklySpendingAmount.push(0)
                }
            })
        }
            console.log("Weekly Spending:")
            console.log({
                weeklyOverviewSpendingAmount:weeklySpendingAmount,
                weeklyOverviewSpendingDate:req.body.weeklyList})

            res.send({
                weeklyOverviewSpendingAmount:weeklySpendingAmount,
                weeklyOverviewSpendingDate:req.body.weeklyList})
       
    }

    if(req.body.timeline == "monthly"){
        console.log("START MONTHLY")
        for(var date of req.body.monthlyList){
            await Transaction.find({email:req.body.email, date: date})
            .then(function(results){
    
    
                for(result of results){
                    if(result.transaction=="Expense"){
                        sum += result.amount
                    }
                }
                if(sum != 0){
                    monthlySpendingAmount.push(sum)
                    sum = 0
                }
                else{
                    monthlySpendingAmount.push(0)
                }
            })
        }
            console.log("Monthly Spending:")
            console.log({
                monthlyOverviewSpendingAmount:monthlySpendingAmount,
                monthlyOverviewSpendingDate:req.body.monthlyList})

            res.send({
                monthlyOverviewSpendingAmount:monthlySpendingAmount,
                monthlyOverviewSpendingDate:req.body.monthlyList})
       
    }
    
    
        
    
})
   


app.post('/api/getGraphCategoricalData', async function(req,res){

    var dailySpendingAmount = []
    var weeklySpendingAmount = []
    var monthlySpendingAmount = []


    if(req.body.timeline == "daily"){

        console.log("CATEGORICAL: START DAILY")

        for(var date of req.body.dailyList){


            await Transaction.find({email:req.body.email, date: date})
            .then(function(results){
                
                for(result of results){
                    if(result.category=="Food"){
                        sum += result.amount
    
                    }
                }
    
                if(sum != 0){
                    dailySpendingAmount.push(sum)
                    sum = 0
                }
                else{
                    dailySpendingAmount.push(0)
                }
            })
        }

    }
})






app.post('/api/updateTransactionData', function(req,res){

    var oldTransaction = {
        email: req.body.email,
        title: req.body.title,
        amount: req.body.amount,
        transaction: req.body.transaction,
        category: req.body.category,
        wallet: req.body.wallet,
        date: req.body.date
    }

    var newTransaction = {
        email: req.body.email,
        title: req.body.updateTitle,
        amount: req.body.updateAmount,
        transaction: req.body.updateTransaction,
        category: req.body.updateCategory,
        wallet: req.body.updateWallet,
        date: req.body.updateDate
    }
    
    Transaction.findOneAndUpdate(oldTransaction, newTransaction)
    .then(function(results){
        console.log("Transaction updated")
        console.log(results)
        res.send(results)
    })
    .catch(function(err){

    })
    
   
})

app.post('/api/updateWalletData', function(req,res){
    
    var oldWallet = {
        email: req.body.email,
        title: req.body.oldTitle,
        type: req.body.oldType,
        currency: req.body.oldCurrency
    }


    var newWallet = {
        email: req.body.email,
        title: req.body.title,
        type: req.body.type,
        currency: req.body.currency
  
    }
    console.log("Old Wallet:")
    console.log(oldWallet)
    console.log("New Wallet:")
    console.log(newWallet)

    Wallet.updateOne(oldWallet, newWallet)
    .then(function(results){
        console.log("Wallet updated")
        console.log(results)
        res.send(newWallet)
    })
    .catch(function(err){
        console.log("Wallet update failed")
        res.send({message: "Wallet update failed"})
    })
    
   
})




app.post('/api/updateGoalData', function(req,res){
    
    var oldGoal = {
    title: req.body.oldTitle,
    targetAmount: req.body.oldTargetAmount,
    wallet: req.body.oldWallet,
    currency: req.body.oldCurrency,
    date: req.body.oldDate,
    
    }

    var newGoal = {
        title: req.body.title,
        targetAmount: req.body.targetAmount,
        wallet: req.body.wallet,
        currency: req.body.currency,
        date: req.body.date,
        
        }

    console.log("Old Goal:")
    console.log(oldGoal)
    console.log("New Goal:")
    console.log(newGoal)

    Goal.updateOne(oldGoal,newGoal)
    .then(function(data){
        console.log("Goal updated")
        console.log(data)
        res.send({message: "Goal updated"})
    })
    .catch(function(err){
        console.log("Goal update failed")
        res.send({message: "Goal update failed"})
    })
})



app.post('/api/updateCategoryData', function(req,res){
    
    var oldCategory = {
        title: req.body.oldTitle,
        email: req.body.email
    }

    var newCategory = {
        title: req.body.newTitle,
        email: req.body.email
    }

    console.log("Old Category:")
    console.log(oldCategory)
    console.log("New Category:")
    console.log(newCategory)

    Category.updateOne()
    .then(function(data){
        console.log("Category updated")
        console.log(data)
        res.send({message: "Category updated"})
    })
    .catch(function(err){
        console.log("Category update failed")
        res.send({message: "Category update failed"})
    })
})



app.post('/api/deleteWalletData', function(req,res){
    

    var wallet = {
        email: req.body.email,
        title: req.body.title,
        type: req.body.type,
        currency: req.body.currency
  
    }
    console.log("Wallet to be deleted:")
    console.log(wallet)
    
    Wallet.deleteOne(wallet)
    .then(function(results){
        console.log("Wallet Deleted")
        console.log(results)
        res.send(wallet)
    })
    .catch(function(err){
        console.log("Wallet Delete Failed")
        res.send({message: "Wallet Delete Failed"})
    })
    
   
})



app.post('/api/deleteGoalData', function(req,res){
    

    var goal = {
        email: req.body.email,
        title: req.body.title,
        targetAmount: req.body.targetAmount,
        acquiredAmount: req.body.acquiredAmount,
        currency: req.body.currency,
        date: req.body.date,
        progress: req.body.acquiredAmount / req.body.targetAmount * 100

  
    }
    console.log("Goal to be deleted:")
    console.log(goal)
    
    Goal.deleteOne(goal)
    .then(function(results){
        console.log("Goal Deleted")
        console.log(results)
        res.send(goal)
    })
    .catch(function(err){
        console.log("Goal Delete Failed")
        res.send({message: "Goal Delete Failed"})
    })



app.post('/api/deleteCategoryData', function(req,res){
    

    var category = {
        email: req.body.email,
        title: req.body.title
  
    }
    console.log("Category to be deleted:")
    console.log(category)
    
    Category.deleteOne(category)
    .then(function(results){
        console.log("Category Deleted")
        console.log(results)
        res.send(category)
    })
    .catch(function(err){
        console.log("Category Delete Failed")
        res.send({message: "Category Delete Failed"})
    })
    
   
})

    
   
})

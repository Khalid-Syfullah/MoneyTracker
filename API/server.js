const express = require('express');
const creatErrors = require('http-errors');
const { PORT } = require('./config/config');
require('./config/init_mongodb');

const userRoute = require('./routes/user.route');
const categoryRoutes = require('./routes/category.route');
const goalRoutes = require('./routes/goal.route');
const incomeRoutes = require('./routes/income.route');
const walletRoutes = require('./routes/wallet.route');
const expenseRoutes = require('./routes/expense.route');

/** definitions */
const app = express();
const myPORT = PORT || 8800;
app.use(express.json());


app.use('/api/user', userRoute);
app.use('/api/category', categoryRoutes);
app.use('/api/goal', goalRoutes);
app.use('/api/income', incomeRoutes);
app.use('/api/wallet', walletRoutes);

/** handle wildcard routes */
app.use(async(req, res, next) => {
    next(creatErrors.NotFound('This route does not exits'))
})

app.use((err, req, res, next) => {
    res.status(err.status || 500)
    res.send({
        error: {
            status: err.status || 500,
            message: err.message || 'Internel server error'
        }
    })
})

/** starting the server */
app.listen(PORT, () => {
    console.log(`server running at port ${myPORT}...`);
})
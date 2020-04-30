const express     = require('express');
const bodyParser  = require('body-parser');
const logger      = require('morgan');
const request     = require('request');
const router      = express.Router();

router
  .route('/api/v1/stock')
  .get(async (req, resp, next) => {

    if(isInvalidStock(req.query['stockCode'])){
      resp.json({});
      return;
    }

    request("https://finance.yahoo.com/quote/" + req.query['stockCode'] + ".sa?p=" + req.query['stockCode'], function(err, res, body){
      try {
        if (err) {
          return;
        }

        var main = JSON.parse(body.split("root.App.main = ")[1].split(";\n}(this));")[0]);
        var quote = {};

        if(main.context.dispatcher.stores.QuoteSummaryStore.financialData !== undefined){
          quote.price = parseFloat(main.context.dispatcher.stores.QuoteSummaryStore.financialData.currentPrice.fmt);
          quote.open = parseFloat(main.context.dispatcher.stores.QuoteSummaryStore.price.regularMarketOpen.fmt);
          quote.high = parseFloat(main.context.dispatcher.stores.QuoteSummaryStore.price.regularMarketDayHigh.fmt);
          quote.low = parseFloat(main.context.dispatcher.stores.QuoteSummaryStore.price.regularMarketDayLow.fmt);
          quote.previousClose = parseFloat(main.context.dispatcher.stores.QuoteSummaryStore.price.regularMarketPreviousClose.fmt);
          quote.volume = parseFloat(main.context.dispatcher.stores.QuoteSummaryStore.price.regularMarketVolume.fmt);
          quote.marketChange = parseFloat(main.context.dispatcher.stores.QuoteSummaryStore.price.regularMarketChange.fmt);
          quote.shortName = main.context.dispatcher.stores.QuoteSummaryStore.price.shortName;
          quote.longName = main.context.dispatcher.stores.QuoteSummaryStore.price.longName;
        }
        
        resp.json(quote);
      } catch (e) {
        resp.json({});
      }
    });
  });

function isInvalidStock(code){
  const n = code.substring(code.length - 1)
  return !(!!n.trim() && n > -1);
}

const PORT        = process.env.PORT || 3000;
const NODE_ENV    = process.env.NODE_ENV || 'development';
const app         = express();

app.set('port', PORT);
app.set('env', NODE_ENV);

app.use(logger('tiny'));
app.use(bodyParser.json());

app.use('/', router);
app.use((req, res, next) => {
  const err = new Error(`${req.method} ${req.url} Not Found`);
  err.statclus = 404;
  next(err);
});
app.use((err, req, res, next) => {
  console.error(err);
  res.status(err.status || 500);
  res.json({
    error: {
      message: err.message,
    },
  });
});

app.listen(PORT, () => {
  console.log(
    `Express Server started on Port ${app.get(
      'port'
    )} | Environment : ${app.get('env')}`
  );
});

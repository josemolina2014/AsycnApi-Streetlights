const Hermes = require('hermesjs');
const app = new Hermes();
const { cyan, gray, yellow } = require('colors/safe');
const buffer2string = require('./middlewares/buffer2string');
const string2json = require('./middlewares/string2json');
const json2string = require('./middlewares/json2string');
const logger = require('./middlewares/logger');
const errorLogger = require('./middlewares/error-logger');
const config = require('../lib/config');
const MqttAdapter = require('hermesjs-mqtt');
const smartylightingStreetlights10EventStreetlightIdLightingMeasured = require('./routes/smartylighting-streetlights-1-0-event-{streetlightId}-lighting-measured.js');
const smartylightingStreetlights10ActionStreetlightIdTurnOn = require('./routes/smartylighting-streetlights-1-0-action-{streetlightId}-turn-on.js');
const smartylightingStreetlights10ActionStreetlightIdTurnOff = require('./routes/smartylighting-streetlights-1-0-action-{streetlightId}-turn-off.js');
const smartylightingStreetlights10ActionStreetlightIdDim = require('./routes/smartylighting-streetlights-1-0-action-{streetlightId}-dim.js');

app.addAdapter(MqttAdapter, config.broker.mqtt);

app.use(buffer2string);
app.use(string2json);
app.use(logger);

// Channels
console.log(yellow.bold.inverse(' PUB '), gray('Will eventually publish to'), yellow('smartylighting/streetlights/1/0/event/{streetlightId}/lighting/measured'));
app.useOutbound(smartylightingStreetlights10EventStreetlightIdLightingMeasured);
console.log(cyan.bold.inverse(' SUB '), gray('Subscribed to'), yellow('smartylighting/streetlights/1/0/action/{streetlightId}/turn/on'));
app.use(smartylightingStreetlights10ActionStreetlightIdTurnOn);
console.log(cyan.bold.inverse(' SUB '), gray('Subscribed to'), yellow('smartylighting/streetlights/1/0/action/{streetlightId}/turn/off'));
app.use(smartylightingStreetlights10ActionStreetlightIdTurnOff);
console.log(cyan.bold.inverse(' SUB '), gray('Subscribed to'), yellow('smartylighting/streetlights/1/0/action/{streetlightId}/dim'));
app.use(smartylightingStreetlights10ActionStreetlightIdDim);

app.use(errorLogger);
app.useOutbound(logger);
app.useOutbound(json2string);

app
  .listen()
  .then((adapters) => {
    console.log(cyan.underline(`${config.app.name} ${config.app.version}`), gray('is ready!'), '\n');
    adapters.forEach(adapter => {
      console.log('🔗 ', adapter.name(), gray('is connected!'));
    });
  })
  .catch(console.error);

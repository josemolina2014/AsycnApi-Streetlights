const Router = require('hermesjs/lib/router');
const router = new Router();
const smartylightingStreetlights10ActionStreetlightIdTurnOffHandler = require('../handlers/smartylighting-streetlights-1-0-action-{streetlightId}-turn-off');
module.exports = router;

router.use('smartylighting/streetlights/1/0/action/:streetlightId/turn/off', async (message, next) => {
  try {
    await smartylightingStreetlights10ActionStreetlightIdTurnOffHandler.turnOff({message});
    next();
  } catch (e) {
    next(e);
  }
});

const Router = require('hermesjs/lib/router');
const router = new Router();
const smartylightingStreetlights10ActionStreetlightIdTurnOnHandler = require('../handlers/smartylighting-streetlights-1-0-action-{streetlightId}-turn-on');
module.exports = router;

router.use('smartylighting/streetlights/1/0/action/:streetlightId/turn/on', async (message, next) => {
  try {
    await smartylightingStreetlights10ActionStreetlightIdTurnOnHandler.turnOn({message});
    next();
  } catch (e) {
    next(e);
  }
});

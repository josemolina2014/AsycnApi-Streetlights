const Router = require('hermesjs/lib/router');
const router = new Router();
const smartylightingStreetlights10ActionStreetlightIdDimHandler = require('../handlers/smartylighting-streetlights-1-0-action-{streetlightId}-dim');
module.exports = router;

router.use('smartylighting/streetlights/1/0/action/:streetlightId/dim', async (message, next) => {
  try {
    await smartylightingStreetlights10ActionStreetlightIdDimHandler.dimLight({message});
    next();
  } catch (e) {
    next(e);
  }
});

const Router = require('hermesjs/lib/router');
const router = new Router();
const smartylightingStreetlights10EventStreetlightIdLightingMeasuredHandler = require('../handlers/smartylighting-streetlights-1-0-event-{streetlightId}-lighting-measured');
module.exports = router;

/**
 * Receive information about environmental lighting conditions of a particular streetlight.
 */
router.useOutbound('smartylighting/streetlights/1/0/event/:streetlightId/lighting/measured', async (message, next) => {
  try {
    await smartylightingStreetlights10EventStreetlightIdLightingMeasuredHandler.receiveLightMeasurement({message});
    next();
  } catch (e) {
    next(e);
  }
});

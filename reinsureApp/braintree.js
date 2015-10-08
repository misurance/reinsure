var dynamicBridge = require("remote-js").dynamicBridge;
var remoteExecute = require("remote-js").reactRemoteExecute;

var proxy = dynamicBridge("braintree", remoteExecute);
proxy.registerMethod("startBraintreeDropIn");
proxy.registerObservable("paymentRegistration");
module.exports = proxy.build();

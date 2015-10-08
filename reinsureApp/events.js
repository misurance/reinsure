var dynamicBridge = require("remote-js").dynamicBridge;
var remoteExecute = require("remote-js").reactRemoteExecute;

var proxy = dynamicBridge("events", remoteExecute);
proxy.registerMethod("stop");
proxy.registerMethod("start");
proxy.registerObservable("stream");
module.exports = proxy.build();

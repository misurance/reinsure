'use strict';

var React = require('react-native');
var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
} = React;

var OnboardingScreen = require('./OnboardingScreen');
var HomeScreen = require('./HomeScreen');

var reinsureApp = React.createClass({
  render: function() {
    return (
      <View style={styles.container}>
        <OnboardingScreen
          style={{flex: 1}}>
        </OnboardingScreen>
      </View>
    );
  }
});

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },

});

AppRegistry.registerComponent('reinsureApp', () => reinsureApp);

'use strict';

var React = require('react-native');
var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Image
} = React;

var OnboardingScreen = require('./OnboardingScreen');
var HomeScreen = require('./HomeScreen');

var reinsureApp = React.createClass({
  render: function() {
    return (
        <Image style={styles.container} source={{uri: 'https://misurance.herokuapp.com/images/bg.jpg'}}>
          <OnboardingScreen
            style={{flex: 1}}>
          </OnboardingScreen>
        </Image>

    );
  }
});

var styles = StyleSheet.create({

  container:{
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'blue',
   resizeMode: 'cover'
  }

});

AppRegistry.registerComponent('reinsureApp', () => reinsureApp);

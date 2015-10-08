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
    getInitialState: function() {
      return {
        onboardingCompleted: false
      }
  },
  render: function() {
    if (this.state.onboardingCompleted) {
      return (
          <Image style={styles.container} source={{uri: 'https://misurance.herokuapp.com/images/bg.jpg'}}>
            <HomeScreen
              style={{flex: 1}}>
            </HomeScreen>
          </Image>
      );
    }
    else {
      return (
          <Image style={styles.container} source={{uri: 'https://misurance.herokuapp.com/images/bg.jpg'}}>
            <OnboardingScreen
              completed={this._onboardingCompleted}
              style={{flex: 1}}>
            </OnboardingScreen>
          </Image>

      );
    }
  },
  _onboardingCompleted: function(){
    this.setState({onboardingCompleted: true});
  }
});

var styles = StyleSheet.create({

  container:{
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    resizeMode: 'stretch'
  }

});

AppRegistry.registerComponent('reinsureApp', () => reinsureApp);

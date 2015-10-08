'use strict';

var React = require('react-native');
var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Image,
  AsyncStorage

} = React;

var OnboardingScreen = require('./OnboardingScreen');
var HomeScreen = require('./HomeScreen');
var SplashScreen = require('./SplashScreen');

var reinsureApp = React.createClass({
    getInitialState: function() {
      return {
        startScreen: 'SplashScreen'
      }
  },
  componentDidMount : async function(){
      setTimeout(this._setScreenByOnboardingStatus, 1500);

  },
  render: function() {
    if (this.state.startScreen === 'HomeScreen') {
      return (
          <Image style={styles.container} source={{uri: 'https://misurance.herokuapp.com/images/bg.jpg'}}>
            <HomeScreen
              style={{flex: 1}}>
            </HomeScreen>
          </Image>
      );
    }
    else if (this.state.startScreen === 'OnboardingScreen'){
      return (
          <Image style={styles.container} source={{uri: 'https://misurance.herokuapp.com/images/bg.jpg'}}>
            <OnboardingScreen
              completed={this._onboardingCompleted}
              style={{flex: 1}}>
            </OnboardingScreen>
          </Image>

      );
    }
    else if (this.state.startScreen === 'SplashScreen'){
      return (
          <Image style={styles.container} source={{uri: 'https://misurance.herokuapp.com/images/bg.jpg'}}>
            <SplashScreen
              style={{flex: 1}}>
            </SplashScreen>
          </Image>

      );
    }
  },
  _onboardingCompleted: async function(){
    console.log('_onboardingCompleted');
    await AsyncStorage.setItem('OnboardingCompleted', 'true');

    console.log('storage set');
    await this._setScreenByOnboardingStatus();
  },
  _setScreenByOnboardingStatus: async function(){
    var value = await AsyncStorage.getItem('OnboardingCompleted')
      if (value){
        this.setState({startScreen: 'HomeScreen'});
      }
      else {
        this.setState({startScreen: 'OnboardingScreen'});
      }
  }
});

var styles = StyleSheet.create({

  container:{
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  }

});

AppRegistry.registerComponent('reinsureApp', () => reinsureApp);

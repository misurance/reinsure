'use strict';

var React = require('react-native');
var {
  Image,
  PixelRatio,
  ScrollView,
  StyleSheet,
  Text,
  View,
  ProgressBarAndroid
} = React;


var SplashScreen = React.createClass({
  render: function() {
    return (
      <View contentContainerStyle={styles.container}>
        <Image style={styles.logo} source={{uri: 'https://misurance.herokuapp.com/images/logo.png'}}/>
        <ProgressBarAndroid style={styles.progress} styleAttr="Inverse" />
      </View>
    );
  },
});

var styles = StyleSheet.create({
  container:{
  },
  logo:{
    height: 192,
    width:250,
    resizeMode: 'cover'
  },
  progress:{
    alignSelf:'center',
  }
});

module.exports = SplashScreen;

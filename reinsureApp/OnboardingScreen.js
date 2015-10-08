'use strict';

var React = require('react-native');
var {
  Image,
  PixelRatio,
  ScrollView,
  StyleSheet,
  Text,
  Image,
  View,
  TouchableHighlight
} = React;
var Button = require('apsl-react-native-button');

var braintree = require('./braintree');

var OnboardingScreen = React.createClass({
  render: function() {
    return (
      <View style={styles.container}>
        <Image style={styles.logo} source={{uri: 'https://misurance.herokuapp.com/images/logo.png'}}/>
        <Text style={styles.subHeader}>
          Hello!
        </Text>
        <Text style={styles.subHeader}>
          Welcome to Misurance, the car insurance that saves you money by presonalizing your premium to your driving habits.
        </Text>
        <Button
          style={styles.button} textStyle={styles.buttonText}
          onPress={() => {
            console.log('world!')
            braintree.startBraintreeDropIn();
          }}>Connect with Braintree
        </Button>
      </View>
    );
  },
  _handlePress: function() {
    console.log('button clicked');
  },
});
console.log(require('image!logo'));

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    marginLeft: 20,
    marginRight: 20,
    width:250
    // backgroundColor:'yellow'
  },
  header: {
    fontSize:32,
    textAlign:'center',
    marginBottom:20,

  },
  logo:{
    height: 192,
    width:250,
    resizeMode: 'cover'
  },
  subHeader: {
    textAlign:'center',
    justifyContent: 'center',
    fontSize:14,
    lineHeight:20

  },
  button: {
    marginTop:30,
    backgroundColor: 'gray',
    borderRadius: 20,
    borderWidth: 0,
    justifyContent:'center'
  },
  buttonText: {
    color:'white',

  }
});

module.exports = OnboardingScreen;

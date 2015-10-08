'use strict';

var React = require('react-native');
var {
  Image,
  PixelRatio,
  ScrollView,
  StyleSheet,
  Text,
  View,
  TouchableHighlight
} = React;
var Button = require('apsl-react-native-button');

var braintree = require('./braintree');

var OnboardingScreen = React.createClass({
  render: function() {
    return (
      <View style={styles.container}>
        <Text style={styles.header}>
          Welcome to misurance!
        </Text>
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
  subHeader: {
    textAlign:'center',
    justifyContent: 'center',
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

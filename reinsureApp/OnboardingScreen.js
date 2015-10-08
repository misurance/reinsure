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
var Button = require('react-native-button');

var OnboardingScreen = React.createClass({
  render: function() {
    return (
      <View style={styles.container}>
        <Text style={styles.header}>
          Welcome to misurance!
        </Text>
        <Text style={styles.subHeader}>
          Car insurance pricing is almost flat. We offer a solution for pay-as-you-go car insurance. If your car's at rest 6 days a week, you shouldn't pay as much as the guy who's driving to work every day. We're implementing real-time risk analysis algorithms, combined with hardware that''s plugged into your car, to calculate your personalized premium.
        </Text>
        <Button style={styles.button} onPress={this._handlePress}>
          Connect with braintree
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
    alignItems: 'center',
    justifyContent: 'flex-start',
    // backgroundColor:'yellow'
  },
  header: {
    justifyContent: 'center',
    fontSize:32

  },
  subHeader: {
    marginTop:10,
    justifyContent: 'center',
  },
  button: {
    marginTop:30
  }
});

module.exports = OnboardingScreen;

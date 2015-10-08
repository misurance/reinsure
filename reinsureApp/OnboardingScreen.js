'use strict';

var React = require('react-native');
var {
  Image,
  PixelRatio,
  ScrollView,
  StyleSheet,
  Text,
  View,
} = React;


var OnboardingScreen = React.createClass({
  render: function() {
    return (
      <View contentContainerStyle={styles.container}>
        <Text>
        Onboarding Screen
        </Text>
      </View>
    );
  },
});

var styles = StyleSheet.create({
  container: {
  },
});

module.exports = OnboardingScreen;

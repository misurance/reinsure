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


var HomeScreen = React.createClass({
  render: function() {
    return (
      <View contentContainerStyle={styles.container}>
        <Text>
        Home Screen
        </Text>
      </View>
    );
  },
});

var styles = StyleSheet.create({
  container: {
  },
});

module.exports = HomeScreen;

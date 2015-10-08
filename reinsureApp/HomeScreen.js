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
  getInitialState: function(){
    return {
      premium:5,
      events: [
        '14:22 Drive started',
      ]
    };
  },
  render: function() {
    var createEventItem = (event) => <Text >{event}</Text>;

    return (
      <View style={styles.container}>
        <Image style={styles.logo} source={{uri: 'https://misurance.herokuapp.com/images/smallLogo.png'}}/>

        <Text style={styles.header}>
          Daily Premium
        </Text>
        <Text style={styles.premium}>
          ₪ {this.state.premium}
        </Text>
        <View style={styles.separator}></View>
        <View style={styles.feedContainer}>
        <ScrollView contentContainerStyle={styles.feed}>
          {this.state.events.map(createEventItem)}
        </ScrollView>

        </View>

        <View style={styles.separator}></View>
        <View style={styles.footer}></View>
      </View>
    );
  },
  _buildItems:function(events){
    return
  }
});

var styles = StyleSheet.create({
  container: {
    flex:1,
    flexDirection:'column',
    alignItems:'center',
    // backgroundColor: 'yellow',
    alignSelf: 'stretch',
  },
  logo:{
    marginTop:40,
    height: 30,
    width:80,
    resizeMode: 'cover',

  },
  header: {
    marginTop:40
  },
  premium:{
    marginTop:10,
    fontSize:24
  },
  separator: {
    borderWidth:0.5,
    borderColor:'black',
    height:1,
    alignSelf: 'stretch',
    marginLeft:70,
    marginRight:70,
    marginTop:10
  },
  feedContainer: {
    flex:1,
    height:200,
    alignSelf: 'stretch',
    marginLeft:70,
    marginRight:70,
    paddingTop:10

  },
  feed:{
    alignSelf: 'stretch',
  },
  footer : {
    height:50
  }

});

module.exports = HomeScreen;

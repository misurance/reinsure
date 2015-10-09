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
var feed  = require('./feed');

var HomeScreen = React.createClass({
  getInitialState: function(){
    return {
      premium:5,
      events: [
       ]
    };
  },
  componentDidMount: function(){
    var self = this;
    feed.stream()
    .doOnNext(function(evt){
      var obj = JSON.parse(evt);
      if (obj.type === 'stateChanged'){
        self.state.events.unshift(obj);
        self.setState({events: self.state.events});

      }
      else if (obj.type === 'updatePremium'){
        self.setState({premium: obj.premium.toFixed(1)});
      }
      console.log(obj);
    })
    .subscribe();

   feed.start();
  },
  render: function() {
    var createEventItem = (event) => <Text key  ={event.key}>{event.time} {event.newState}</Text>;

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

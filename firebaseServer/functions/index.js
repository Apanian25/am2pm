const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.helloWorld = functions.https.onRequest((request, response) => {
  response.send("Hello from Firebase!");
 });

exports.newNewsArticle = functions.database.ref('/newArticles/{id}').onCreate((snapshot, event) => {
    console.log('This got triggerd');
	
	var message = {
		"notification": {
		  "title": "New article uploaded!!!",
		  "body": snapshot._data.title
		}
	};

    return admin.messaging().sendToTopic("News", message)
    .then((response) => {
       // Response is a message ID string.
       console.log('Successfully sent message:', response);
       return null;
     })
     .catch((error) => {
       console.log('Error sending message:', error);
     });
});

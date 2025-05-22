const admin = require('firebase-admin');
const serviceAccount = require('./service-account.json'); // Asegúrate de que el archivo esté en la misma carpeta

// Inicializa Firebase Admin SDK
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

// Token del dispositivo al que se le enviará el mensaje
const deviceToken = 'cWKQTGVxSjarrMmu-bXmW5:APA91bFtwCKDtRcXMrrW27jt_vv7HHZOP3GWYbzbvNLiABvOJ-IXyzg25qhjiEuI7WGxmb2GgC7LJ3tYXFSziDqFsB32mS-0hxAPMeiERYGygPClaTcfLmE'; // Reemplaza esto con el token real

// Construye el mensaje
const message = {
  token: deviceToken,
  notification: {
    title: 'Hola desde Firebase!',
    body: 'Este es un mensaje de prueba enviado con Node.js y Firebase',
  },
};

// Envía el mensaje
admin.messaging().send(message)
  .then((response) => {
    console.log('Mensaje enviado exitosamente:', response);
  })
  .catch((error) => {
    console.error('Error al enviar el mensaje:', error);
  });

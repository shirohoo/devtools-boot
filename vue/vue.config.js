module.exports = {
  chainWebpack: config => {
    config
      .plugin('html')
      .tap(args => {
        args[0].title = 'DEVELOPMENT DICTIONARY';
        return args;
      });
  },

  outputDir: '../src/main/resources/static',

  // devServer: {
  //   proxy: {
  //     '/api': {
  //       target      : 'http://localhost:8080',
  //       changeOrigin: true,
  //       ws          : true
  //     }
  //   }
  // },

  transpileDependencies: [
    'vuetify'
  ]
}

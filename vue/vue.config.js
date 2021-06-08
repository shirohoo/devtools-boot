module.exports = {
  outputDir: '../src/main/resources/static',

  devServer: {
    proxy: {
      '/api': {
        target      : 'http://localhost:8080',
        changeOrigin: true,
        ws          : true
      }
    }
  },

  transpileDependencies: [
    'vuetify'
  ]
}

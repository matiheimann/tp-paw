// Karma configuration

module.exports = function(config) {
  config.set({

    basePath: '',

    frameworks: ['jasmine', 'requirejs'],

    files: [
      'test-main.js',
      {pattern: 'bower_components/**/*.js', included: false},
      {pattern: 'app/scripts/**/*.js', included: true},
      {pattern: 'app/tests/**/*.js', included: true},
    ],

    exclude: [
      'app/scripts/build.js',
      'bower_components/**/test/**'
    ],

    preprocessors: {
    },

    reporters: ['spec'],

    port: 9876,

    colors: true,

    logLevel: config.LOG_INFO,

    autoWatch: false,

    browsers: ['PhantomJS'],

    singleRun: true,

    concurrency: Infinity
  })
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description cli配置
 */

const path = require('path')
const {
  publicPath,
  assetsDir,
  outputDir,
  lintOnSave,
  transpileDependencies,
  title,
  abbreviation,
  devPort,
  providePlugin,
  build7z,
  donation,
  isOri,
} = require('./src/config')
const { webpackBarName, webpackBanner, donationConsole } = require('zx-layouts')

if (donation) donationConsole()
const { version, author } = require('./package.json')
const Webpack = require('webpack')
const WebpackBar = require('webpackbar')
const FileManagerPlugin = require('filemanager-webpack-plugin')
const dayjs = require('dayjs')
const date = dayjs().format('YYYY_M_D')
const time = dayjs().format('YYYY-M-D HH:mm:ss')
process.env.VUE_APP_TITLE = title || 'vue-admin-beautiful'
process.env.VUE_APP_AUTHOR = author || 'chuzhixin 1204505056@qq.com'
process.env.VUE_APP_UPDATE_TIME = time
process.env.VUE_APP_VERSION = version
process.env.VUE_APP_KEYWORDS = author == 'sdp'
process.env.VUE_APP_DESCRIPTION = author == 'sdp'
process.env.BACKEND_PORT = process.env.BACKEND_PORT || 9999

const resolve = (dir) => path.join(__dirname, dir)
const mockServer = () => {
  if (process.env.NODE_ENV === 'development') return require('./mock')
  else return ''
}

module.exports = {
  publicPath,
  assetsDir,
  outputDir,
  lintOnSave,

  transpileDependencies,
  devServer: {
    hot: true,
    port: devPort,
    proxy: {
      '/api2': {
        changeOrigin: true,
        target: 'http://localhost:' + process.env.BACKEND_PORT,
        pathRewrite: {
          '^/api2': '',
        },
      },
    },
    open: true,
    noInfo: false,
    overlay: {
      warnings: true,
      errors: true,
    },
    after: mockServer(),
  },
  configureWebpack() {
    return {
      resolve: {
        alias: {
          '@': resolve('src'),
        },
      },
      plugins: [
        new Webpack.ProvidePlugin(providePlugin),
        new WebpackBar({
          name: webpackBarName,
        }),
      ],
    }
  },
  chainWebpack(config) {
    config.plugins.delete('preload')
    config.plugins.delete('prefetch')
    config.when(process.env.NODE_ENV !== 'development', (config) => {
      config.performance.set('hints', false)
      config.devtool('none')
      config.optimization.splitChunks({
        automaticNameDelimiter: '-',
        chunks: 'all',
        cacheGroups: {
          chunk: {
            name: 'vab-chunk',
            test: /[\\/]node_modules[\\/]/,
            minSize: 131072,
            maxSize: 524288,
            chunks: 'async',
            minChunks: 2,
            priority: 10,
          },
          vue: {
            name: 'vue',
            test: /[\\/]node_modules[\\/](vue(.*)|core-js)[\\/]/,
            chunks: 'initial',
            priority: 20,
          },
          elementUI: {
            name: 'element-ui',
            test: /[\\/]node_modules[\\/]element-ui(.*)[\\/]/,
            priority: 30,
          },
          extra: {
            name: 'vab-layouts',
            test: resolve('src/layouts'),
            priority: 40,
          },
        },
      })
      config
        .plugin('banner')
        .use(Webpack.BannerPlugin, [`${webpackBanner}${time}`])
        .end()
      if (isOri) {
        config.module
          .rule('images')
          .use('image-webpack-loader')
          .loader('image-webpack-loader')
          .options({
            bypassOnDebug: true,
          })
          .end()
      }
      config.output.filename('js/[name].[chunkhash:8].js').end()
      config.output.chunkFilename('js/[name].[chunkhash:8].js').end()

      config.plugin('extract-css').tap((args) => [
        {
          filename: `css/[name].[chunkhash:8].css`,
          chunkFilename: `css/[name].[chunkhash:8].css`,
        },
      ])
    })

    if (build7z) {
      config.when(process.env.NODE_ENV === 'production', (config) => {
        config
          .plugin('fileManager')
          .use(FileManagerPlugin, [
            {
              onEnd: {
                delete: [`./${outputDir}/video`, `./${outputDir}/data`],
                archive: [
                  {
                    source: `./${outputDir}`,
                    destination: `./${outputDir}/${abbreviation}_${outputDir}_${date}.7z`,
                  },
                ],
              },
            },
          ])
          .end()
      })
    }
  },
  runtimeCompiler: true,
  productionSourceMap: false,
  css: {
    requireModuleExtension: true,
    sourceMap: true,
    loaderOptions: {
      scss: {
        /*sass-loader 8.0语法 */
        //prependData: '@import "~@/styles/variables.scss";',

        /*sass-loader 9.0写法，感谢github用户 shaonialife*/
        additionalData(content, loaderContext) {
          const { resourcePath, rootContext } = loaderContext
          const relativePath = path.relative(rootContext, resourcePath)
          if (
            relativePath.replace(/\\/g, '/') !== 'src/styles/variables.scss'
          ) {
            return '@import "~@/styles/variables.scss";' + content
          }
          return content
        },
      },
    },
  },
}

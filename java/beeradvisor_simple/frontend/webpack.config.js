const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const HtmlWebpackHarddiskPlugin = require("html-webpack-harddisk-plugin");
const path = require("path");
module.exports = {
  entry: "./src/main.tsx",
  mode: "development",
  output: {
    path: __dirname + "/public/dist/",
    filename: "[name].[chunkhash].js",
    publicPath: "/dist"
  },
  resolve: {
    extensions: [".js", ".jsx", ".ts", ".tsx"]
  },
  module: {
    rules: [
      {
        test: /\.scss$/,
        exclude: /node_modules/,
        use: [
          MiniCssExtractPlugin.loader,
          {
            loader: "css-loader",
            options: {
              importLoaders: 1,
              modules: true,
              sourceMap: true,
              localIdentName: "[path]__[name]___[local]"
            }
          },
          {
            loader: "sass-loader",
            options: {
              sourceMap: true
            }
          }
        ]
      },
      {
        test: /\.svg/,
        loader: "file-loader",
        query: {
          name: "assets/[name].[hash:8].[ext]"
        },
        exclude: /node_modules/
      },
      {
        test: /\.(t|j)sx?$/,
        use: "awesome-typescript-loader",
        exclude: /node_modules/
      },
      { enforce: "pre", test: /\.js$/, loader: "source-map-loader", exclude: [/node_modules/, /build/, /__test__/] }
    ]
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: "[name].[chunkhash].css"
    }),
    new HtmlWebpackPlugin({
      alwaysWriteToDisk: true,
      filename: path.resolve("./public/index.html"),
      template: "public/index-template.html"
    }),
    new HtmlWebpackHarddiskPlugin()
  ],
  devtool: "source-map",
  devServer: {
    historyApiFallback: true,
    port: 9080,
    stats: "minimal"
  }
};

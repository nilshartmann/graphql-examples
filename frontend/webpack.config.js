const ExtractTextPlugin = require("extract-text-webpack-plugin");

module.exports = {
  entry: "./src/main.tsx",
  output: {
    path: __dirname + "/public/dist/",
    filename: "main.js",
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
        use: ExtractTextPlugin.extract({
          fallback: "style-loader",
          use: [
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
        })
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
  plugins: [new ExtractTextPlugin("books-app.css")],
  devtool: "source-map",
  devServer: {
    historyApiFallback: true,
    port: 9080
  }
};

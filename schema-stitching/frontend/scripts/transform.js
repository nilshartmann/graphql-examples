const path = require("path");
const babylon = require("babylon");

// https://github.com/facebook/jscodeshift#transform-module
function transformer(file, api) {
  // file.path: src/x/y/__generated__/ZzzQuery.ts
  if (file.path.indexOf("__generated__") === -1) {
    return;
  }

  const fileName = path.basename(file.path);
  const queryName = fileName.substring(0, fileName.indexOf(".ts")); //  + "Query";
  const queryResultName = queryName + "Result";

  console.log(`[${fileName}] Renaming '${queryName}' to '${queryResultName}'`);

  const j = api.jscodeshift;

  return j(file.source)
    .find(j.Identifier)
    .filter(p => p.node.name.indexOf(queryName + "_") !== -1 || p.node.name.endsWith(queryName))
    .forEach(path => {
      j(path).replaceWith(j.identifier(path.node.name.replace(queryName, queryResultName)));
    })
    .toSource();
}

const options = {
  sourceType: "module",
  allowImportExportEverywhere: true,
  allowReturnOutsideFunction: true,
  plugins: ["typescript"]
};

const parser = {
  parse: function(source) {
    return babylon.parse(source, options);
  }
};

export { parser, transformer as default };

require("ts-node/register");

process.on("warning", e => console.warn(e.stack));

require("./src/Stitcher");

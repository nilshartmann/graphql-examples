module.exports = {
  client: {
    includes: ["src/**/*.{ts,tsx}"],
    service: {
      name: "beeradvisor",
      url: "http://localhost:9000/graphql"
    }
  }
};

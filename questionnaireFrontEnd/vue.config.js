module.exports = {
    devServer: {
        port: 81,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
            },

        },
    },
};

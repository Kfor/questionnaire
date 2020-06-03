module.exports = {
    publicPath: '/',
    devServer: {
        port: 80,
        proxy: {
            '/': {
                target: 'http://localhost:8081',
                changeOrigin: true,
                pathRewrite: {
                    '^/': ''
                }
            },
            '/api': {
                target: 'http://localhost:8081',
                changeOrigin: true,
            },
        },
    },
};

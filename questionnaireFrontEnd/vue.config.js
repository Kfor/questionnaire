module.exports = {
    devServer: {
        port: 80,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
            },
            '/register':{
                target: 'http://localhost:8080',
                changeOrigin: true,
            }

        },
    },
};

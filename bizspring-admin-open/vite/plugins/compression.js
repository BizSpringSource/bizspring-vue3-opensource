import compression from 'vite-plugin-compression'

export default function createCompression(env) {
    return [compression({
        ext: '.gz',
        deleteOriginFile: true
    })]
}

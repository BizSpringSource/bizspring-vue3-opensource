import cjs from "rollup-plugin-commonjs";
import { uglify } from 'rollup-plugin-uglify';

module.exports = [
    {
        input: './recorder.js',
        plugins: [
            cjs()
        ],
        output: [
            {
                name: 'Recorder',
                file: './recorder.build.js',
                format: 'umd',
                strict: false,
                sourcemap: false
            }
        ]
    },
    {
        input: './recorder.js',
        plugins: [
            cjs(),
            uglify({
                compress: {},
                mangle: {},
                ie8: false,
                warnings: true
            })
        ],
        output: [
            {
                name: 'Recorder',
                file: './recorder.build.min.js',
                format: 'umd',
                strict: false,
                sourcemap: false
            }
        ]
    }
];

import sourcemaps from "rollup-plugin-sourcemaps";
import resolve from "@rollup/plugin-node-resolve";
import babel from "@rollup/plugin-babel";
import cjs from "@rollup/plugin-commonjs";
import replace from 'rollup-plugin-re';
import { terser } from "rollup-plugin-terser";

export default [
    {
        input: './src/BenzAMRRecorder.js',
        plugins: [
            sourcemaps(),
            resolve({
                jsnext: true,
                browser: true
            }),
            cjs(),
            babel({
                babelHelpers: 'bundled',
                exclude: ['./node_modules/**', './src/amrnb.js']
            }),
            // https://github.com/rollup/rollup/wiki/Troubleshooting#avoiding-eval
            replace({
                include: ['./src/amrnb.js'],
                replaces: {
                    'eval(': '[eval][0]('
                }
            })
        ],
        output: [
            {
                name: 'BenzAMRRecorder',
                file: './BenzAMRRecorder.js',
                format: 'umd',
                strict: false,
                sourcemap: true
            }
        ]
    },
    {
        input: './src/BenzAMRRecorder.js',
        plugins: [
            resolve({
                jsnext: true,
                browser: true
            }),
            cjs(),
            babel({
                babelHelpers: 'bundled',
                exclude: ['./node_modules/benz-recorderjs/**', './src/amrnb.js']
            }),
            // https://github.com/rollup/rollup/wiki/Troubleshooting#avoiding-eval
            replace({
                include: ['./src/amrnb.js'],
                replaces: {
                    'eval(': '[eval][0]('
                }
            }),
            terser({
                compress: {},
                mangle: {
                    safari10: true,
                    properties: {
                        regex: /^_[^_]/
                    }
                },
                ie8: false,
                safari10: true,
                warnings: true
            })
        ],
        output: [
            {
                name: 'BenzAMRRecorder',
                file: './BenzAMRRecorder.min.js',
                format: 'umd',
                strict: false,
                sourcemap: false
            }
        ]
    }
]

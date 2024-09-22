module.exports = {
    root: true,
    parserOptions: {
        parser: 'babel-eslint',
        sourceType: 'module'
    },
    env: {
        browser: true,
        node: true,
        es6: true
    },
    extends: ['plugin:vue/recommended', 'eslint:recommended'],

    // add your custom rules here
    // it is base on https://github.com/vuejs/eslint-config-vue
    rules: {
        'vue/valid-template-root': 'off',
        'vue/order-in-components': 'off',
        'vue/valid-v-model': 'off',
        'vue/max-attributes-per-line': [2, {
            'singleline': 10,
            'multiline': {
                'max': 1,
                'allowFirstLine': false
            }
        }],
        'vue/html-self-closing': ['error', {
            'html': {
                'void': 'any',
                'normal': 'any',
                'component': 'any'
            },
            'svg': 'any',
            'math': 'any'
        }],
        'vue/singleline-html-element-content-newline': 'off',
        'vue/no-parsing-error': [2, {'invalid-first-character-of-tag-name': false}],
        'vue/no-use-v-if-with-v-for': 'off',
        'vue/multiline-html-element-content-newline': 'off',
        'vue/name-property-casing': [0, 'PascalCase'],
        'vue/html-closing-bracket-newline': [0, {
            'singleline': 'never',
            'multiline': 'always'
        }],
        'vue/no-side-effects-in-computed-properties': 0,
        'accessor-pairs': 2, //在对象中使用getter/setter
        'arrow-spacing': [2, {
            'before': true,
            'after': true
        }], //=>的前/后括号
        'block-spacing': [2, 'always'], //块是否需要空格
        // if while function 后面的{必须与if在同一行，java风格。
        'brace-style': [2, '1tbs', {
            'allowSingleLine': true
        }],
        //强制驼峰法命名
        'camelcase': [0, {
            'properties': 'always'
        }],
        // 数组和对象键值对最后一个逗号， never参数：不能带末尾的逗号, always参数：必须带末尾的逗号，
        // always-multiline：多行模式必须带逗号，单行模式不能带逗号
        'comma-dangle': [2, 'never'],
        // 控制逗号前后的空格
        'comma-spacing': [2, {
            'before': false,
            'after': true
        }],
        'comma-style': [2, 'last'], // 控制逗号在行尾出现还是在行首出现
        // 强制在子类构造函数中用super()调用父类构造函数，TypeScrip的编译器也会提示
        'constructor-super': 2,
        // 强制object.key 中 . 的位置，参数:
        // property，'.'号应与属性在同一行
        // object, '.' 号应与对象名在同一行
        'dot-location': [2, 'property'],
        'eol-last': 2, // 文件末尾强制换行
        'eqeqeq': [2, 'allow-null'], // 使用 === 替代 ==
        'generator-star-spacing': 0, //生成器函数*的前后空格
        'handle-callback-err': 0, //nodejs 处理错误
        'indent': [2, 2, { //缩进风格
            'SwitchCase': 1
        }],
        // JSX 属性中一致使用双引号或单引号
        'jsx-quotes': [2, 'prefer-single'],
        //对象字面量中冒号的前后空格
        'key-spacing': [2, {
            'beforeColon': false,
            'afterColon': true
        }],
        'keyword-spacing': [2, {
            'before': true,
            'after': true
        }],
        //函数名首行大写必须使用new方式调用，首行小写必须用不带new方式调用
        'new-cap': [2, {
            'newIsCap': true,
            'capIsNew': false
        }],
        'new-parens': 2, //new时必须加小括号
        'no-array-constructor': 2, //禁止使用数组构造器
        'no-caller': 2, //禁止使用arguments.caller或arguments.callee
        'no-console': process.env.NODE_ENV === 'production' ? 2 : 0, //禁止使用console
        'no-class-assign': 2, //禁止给类赋值
        'no-cond-assign': 2, //禁止在条件表达式中使用赋值语句
        'no-const-assign': 2, //禁止修改const声明的变量
        'no-control-regex': 0, //禁止在正则表达式中使用控制字符
        'no-delete-var': 2, //不能对var声明的变量使用delete操作符
        'no-dupe-args': 2, //函数参数不能重复
        'no-dupe-class-members': 2, //不允许类中出现重复的声明
        'no-dupe-keys': 2, //在创建对象字面量时不允许键重复 {a:1,a:1}
        'no-duplicate-case': 2, //switch中的case标签不能重复
        'no-empty-character-class': 0, //正则表达式中的[]内容不能为空
        'no-empty-pattern': 0,
        'no-eval': 0, //禁止使用eval
        'no-ex-assign': 2, //禁止给catch语句中的异常参数赋值
        'no-extend-native': 2, //禁止扩展native对象
        'no-extra-bind': 2, //禁止不必要的函数绑定
        'no-extra-boolean-cast': 2, //禁止不必要的bool转换
        'no-extra-parens': [2, 'functions'], //禁止非必要的括号
        'no-fallthrough': 2, //禁止switch穿透
        'no-floating-decimal': 2, //禁止省略浮点数中的0 .5 3.
        'no-func-assign': 2, //禁止重复的函数声明
        'no-implied-eval': 2, //禁止使用隐式eval
        'no-inner-declarations': [2, 'functions'], //禁止在块语句中使用声明（变量或函数）
        'no-invalid-regexp': 2, //禁止无效的正则表达式
        'no-irregular-whitespace': 2, //不能有不规则的空格
        'no-iterator': 2, //禁止使用__iterator__ 属性
        'no-label-var': 2, //label名不能与var声明的变量名相同
        'no-labels': [2, { //禁止标签声明
            'allowLoop': false,
            'allowSwitch': false
        }],
        'no-lone-blocks': 2, //禁止不必要的嵌套块
        'no-mixed-spaces-and-tabs': 2, //禁止混用tab和空格
        'no-multi-spaces': 2, //不能用多余的空格
        'no-multi-str': 2, //字符串不能用\换行
        'no-multiple-empty-lines': [0, {
            'max': 1
        }], //空行最多不能超过1行
        'no-native-reassign': 2, //不能重写native对象
        'no-negated-in-lhs': 2, //in 操作符的左边不能有!
        'no-new-object': 2, //禁止使用new Object()
        'no-new-require': 2, //禁止使用new require
        'no-new-symbol': 2, //禁止使用new symbol
        'no-new-wrappers': 2, //禁止使用new创建包装实例，new String new Boolean new Number
        'no-obj-calls': 0, //不能调用内置的全局对象，比如Math() JSON()
        'no-octal': 2, //禁止使用八进制数字
        'no-octal-escape': 2, //禁止使用八进制转义序列
        'no-path-concat': 2, //node中不能使用__dirname或__filename做路径拼接
        'no-proto': 2, //禁止使用__proto__属性
        'no-redeclare': 2, //禁止重复声明变量
        'no-regex-spaces': 2, //禁止在正则表达式字面量中使用多个空格 /foo bar/
        'no-return-assign': [2, 'except-parens'], //return 语句中不能有赋值表达式
        'no-self-assign': 2, //自我分配
        'no-self-compare': 2, //不能比较自身
        'no-sequences': 2, //禁止使用逗号运算符
        'no-shadow-restricted-names': 2, //严格模式中规定的限制标识符不能作为声明时的变量名使用
        'no-spaced-func': 2, //函数调用时 函数名与()之间不能有空格
        'no-sparse-arrays': 2, //禁止稀疏数组， [1,,2]
        'no-this-before-super': 2, //在调用super()之前不能使用this或super
        'no-throw-literal': 2, //禁止抛出字面量错误 throw "error";
        'no-trailing-spaces': 0, //一行结束后面不要有空格
        'no-undef': 2, //不能有未定义的变量
        'no-undef-init': 2, //变量初始化时不能直接给它赋值为undefined
        'no-unexpected-multiline': 0, //避免多行表达式
        'no-unmodified-loop-condition': 0, //检查引用是否在循环中被修改
        'no-unneeded-ternary': 0, //禁止不必要的嵌套 var isYes = answer === 1 ? true : false;
        'no-unreachable': 2, //不能有无法执行的代码
        'no-unsafe-finally': 0,
        'no-unused-vars': [2, {
            'vars': 'all',
            'args': 'none'
        }], //不能有声明后未被使用的变量或参数
        'no-useless-call': 2, //禁止不必要的call和apply
        'no-useless-computed-key': 0, //没有必要使用带文字的计算属性
        'no-useless-constructor': 2, //可以在不改变类的工作方式的情况下安全地移除的类构造函数
        'no-useless-escape': 0,
        'no-whitespace-before-property': 0,
        'no-with': 2, //禁用with
        'one-var': 0, //连续声明
        'operator-linebreak': [2, 'after', {
            'overrides': {
                '?': 'before',
                ':': 'before'
            }
        }], //换行时运算符在行尾还是行首
        'padded-blocks': 0, //块语句内行首行尾是否要空行
        'quotes': [2, 'single', {
            'avoidEscape': true,
            'allowTemplateLiterals': true
        }], //引号类型 `` "" ''
        'semi': [2, 'never'], //语句强制分号结尾
        'semi-spacing': [2, {
            'before': false,
            'after': true
        }], //分号前后空格
        'space-before-blocks': [2, 'always'], //不以新行开始的块{前面要不要有空格
        'space-before-function-paren': [2, 'never'], //函数定义时括号前面要不要有空格
        'space-in-parens': [2, 'never'], //小括号里面要不要有空格
        'space-infix-ops': 2, //中缀操作符周围要不要有空格
        'space-unary-ops': [2, {
            'words': true,
            'nonwords': false
        }], //一元运算符的前/后要不要加空格
        'spaced-comment': 0, //注释风格不要有空格什么的
        "template-curly-spacing": "off",
        "indent": ["error", 2, {
            "ignoredNodes": ["TemplateLiteral"]
        }],
        'use-isnan': 2, //禁止比较时使用NaN，只能用isNaN()
        'valid-typeof': 2, //必须使用合法的typeof的值
        'wrap-iife': [2, 'any'], //立即执行函数表达式的小括号风格
        'yield-star-spacing': 0,
        'yoda': [2, 'never'], //禁止尤达条件
        'prefer-const': 2, //首选const
        'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0, //禁止使用debugger
        'object-curly-spacing': [2, 'always', {
            objectsInObjects: false
        }], //大括号内是否允许不必要的空格
        'array-bracket-spacing': [2, 'never'] //是否允许非空数组里面有多余的空格
    }
}

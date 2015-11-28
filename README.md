# README
提供可现场编辑的 TextView ，以及一个性别选择器，通常用在配置页面上。

## ScreenShots
![](art/1.gif)
![](art/2.gif)

## GenderTextView
```
// 切换性别
tvGender.toggle()

// 返回当前所选性别："male" or "female"
tvGender.getSelected()

// 设置当前选择的性别
tvGender.setSelected("male")
```

## 可现场编辑的 TextView
- 监听软键盘的状态，控制 EditText 显示，覆盖在 TextView 上。

## 应用示例
[ConfigFragment.kt](https://github.com/nekocode/ConfigActivity/blob/master/app/src/main/java/cn/nekocode/configactivity/ui/fragment/ConfigFragment.kt#L49)

## 使用框架
[**kotlin_android_base_framework**](https://github.com/nekocode/kotlin_android_base_framework)

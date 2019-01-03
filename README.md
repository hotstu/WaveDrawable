[![](https://jitpack.io/v/hotstu/WaveDrawable.svg)](https://jitpack.io/#hotstu/WaveDrawable)

# WaveDrawable
打造最接近真实自然的水波loading动画

## 效果

![9.PNG](./screen/9.PNG)
![7.gif](./screen/7.gif)
![8.gif](./screen/8.gif)
![9.gif](./screen/9.gif)

## 使用
1. Add the JitPack repository to your build file
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2. Add the dependency
```
	dependencies {
	        implementation 'com.github.hotstu:WaveDrawable:1.0.0'
	}

```
## 设置
支持以下属性

*  direction
方向，可以是TOP LEFT RIGHT BOTTOM

*  progress
进度 [0, 1]

*  waveHeight
设置波峰高度，px,默认为height * .05f

*  waveStrength
设置波浪强度 数值越大单位区域内峰谷越多,默认0.01f

*  waveSpeed
设置波浪速度 数值越大波浪变化越快,默认0.05f




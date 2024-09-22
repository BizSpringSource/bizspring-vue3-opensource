/**
 * @fileOverview 描述文件
 * @author BenzLeung(https://github.com/BenzLeung)
 * @date 2019/11/30
 *
 * 每位工程师都有保持代码优雅的义务
 * each engineer has a duty to keep the code elegant
 */

export type AMREvent = null|(() => void);
export default class BenzAMRRecorder {
    constructor();

    private _isInit: boolean;
    private _isInitRecorder: boolean;
    private _recorderControl: RecorderControl;
    private _samples: Float32Array;
    private _rawData: Uint8Array;
    private _blob: null|Blob;
    private _onEnded: null|(() => void);
    private _onAutoEnded: null|(() => void);
    private _onPlay: null|(() => void);
    private _onPause: null|(() => void);
    private _onResume: null|(() => void);
    private _onStop: null|(() => void);
    private _onStartRecord: null|(() => void);
    private _onCancelRecord: null|(() => void);
    private _onFinishRecord: null|(() => void);
    private _isPlaying: boolean;
    private _isPaused: boolean;
    private _startCtxTime: Number;
    private _pauseTime: Number;
    private _playEmpty: (() => void);
    private _onEndCallback: (() => void);
    private _runAMRWorker: Function;
    private on(action: string, fn: AMREvent): void;
    private throwAlreadyInitialized: (() => void);

    /**
     * 是否已经初始化
     */
    public isInit(): boolean;

    /**
     * 使用浮点数据初始化
     * @param array
     */
    public initWithArrayBuffer(array: Float32Array): Promise<void>;

    /**
     * 使用 Blob 对象初始化（ <input type="file">）
     * @param blob
     */
    public initWithBlob(blob: Blob): Promise<void>;

    /**
     * 使用 url 初始化
     * @param url
     */
    public initWithUrl(url: string): Promise<void>;

    /**
     * 初始化录音
     */
    public initWithRecord(): Promise<void>;


    /**
     * 播放（重新开始，无视暂停状态）
     * @param startTime 可指定开始位置
     */
    public play(startTime?: number|string): void;

    /**
     * 停止
     */
    public stop(): void;

    /**
     * 暂停
     */
    public pause(): void;

    /**
     * 从暂停处继续
     */
    public resume(): void;

    /**
     * 整合 play() 和 resume()，若在暂停状态则继续，否则从头播放
     */
    public playOrResume(): void;

    /**
     * 整合 resume() 和 pause()
     */
    public pauseOrResume(): void;

    /**
     * 整合 play() 和 resume() 和 pause()
     */
    public playOrPauseOrResume(): void;

    /**
     * 跳转到音频指定位置，不改变播放状态
     * @param time 指定位置（秒，浮点数）
     */
    public setPosition(time: number|string): void;

    /**
     * 获取当前播放位置（秒）
     * @return 位置，秒，浮点数
     */
    public getCurrentPosition(): number;

    /**
     * 是否正在播放
     */
    public isPlaying(): boolean;

    /**
     * 是否暂停中
     */
    public isPaused(): boolean;


    /**
     * 开始录音
     */
    public startRecord(): void;

    /**
     * 结束录音，并把录制的音频转换成 AMR
     */
    public finishRecord(): Promise<void>;

    /**
     * 放弃录音
     */
    public cancelRecord(): void;

    /**
     * 是否正在录音
     * @return {boolean}
     */
    public isRecording(): boolean;

    /**
     * 播放事件
     * @param fn
     */
    public onPlay(fn: AMREvent): void;

    /**
     * 停止事件（包括播放结束）
     * @param fn
     */
    public onStop(fn: AMREvent): void;

    /**
     * 暂停事件
     * @param fn
     */
    public onPause(fn: AMREvent): void;

    /**
     * 继续播放事件
     * @param fn
     */
    public onResume(fn: AMREvent): void;

    /**
     * 播放结束事件
     * @param fn
     */
    public onEnded(fn: AMREvent): void;

    /**
     * 播放完毕自动结束事件
     * @param fn
     */
    public onAutoEnded(fn: AMREvent): void;

    /**
     * 开始录音事件
     * @param fn
     */
    public onStartRecord(fn: AMREvent): void;

    /**
     * 结束录音事件
     * @param fn
     */
    public onFinishRecord(fn: AMREvent): void;

    /**
     * 放弃录音事件
     * @param fn
     */
    public onCancelRecord(fn: AMREvent): void;

    /**
     * 获取音频的时间长度（单位：秒）
     */
    public getDuration(): number;

    /**
     * 获取 AMR 文件的 Blob 对象
     */
    public getBlob(): Blob | null;

    /**
     * 注销，清理内部存储
     */
    public destroy(): void;

    /**
     * 编码 AMR
     * @param samples
     * @param sampleRate
     */
    public encodeAMRAsync(samples: Float32Array, sampleRate: number): Promise<Uint8Array>;

    /**
     * 解码 AMR
     * @param u8Array
     */
    public decodeAMRAsync(u8Array: Uint8Array): Promise<Float32Array>;

    /**
     * AMR 数据转换成 Blob
     * @param data
     */
    public static rawAMRData2Blob(data: Uint8Array): Blob;

    /**
     * 判断浏览器是否支持播放
     */
    public static isPlaySupported(): boolean;

    /**
     * 判断浏览器是否支持录音
     */
    public static isRecordSupported(): boolean;
}

declare class RecorderControl {
    private _recorderStream;
    private _recorderStreamSourceNode;
    private _recorder;
    private _isRecording;
    private _curSourceNode;

    public playPcm(samples: Float32Array, sampleRate: number, onEnded: (() => void), startPos: number): void;
    public stopPcm(): void;
    public stopPcmSilently(): void;
    public initRecorder(): Promise<void>;
    public isRecording(): boolean;
    public startRecord(): void;
    public generateRecordSamples(): Promise<Float32Array>;
    public releaseRecord(): void;
    public static isPlaySupported(): boolean;
    public static isRecordSupported(): boolean;
    public static getCtxSampleRate(): number;
    public static getCtxTime(): number;
    public static decodeAudioArrayBufferByContext(array: ArrayBuffer): Promise<Float32Array>;
}

interface EncodeWorkerMsg {
    command: 'encode',
    samples: Float32Array,
    sampleRate: number,
}

interface DecodeWorkerMsg {
    command: 'decode',
    buffer: Uint8Array,
}

export enum MeasurementType {
    TEMP = "TEMP",
    HR = "HR",
    RR = "RR",
}

interface Measurement {
    type: MeasurementType;
    value?: number;
}

export interface NewsMeasurementData {
    measurements: Measurement[];
}

interface BaseResponse {
    type: 'success' | 'error';
}

export interface NewsMeasurementSuccessResponse extends BaseResponse {
    type: 'success';
    score: number;
}

export interface NewsMeasurementErrorResponse extends BaseResponse {
    type: 'error';
    status: number;
    message: string;
    errorCode: string;
}

export type NewsMeasurementResponse = NewsMeasurementSuccessResponse | NewsMeasurementErrorResponse;


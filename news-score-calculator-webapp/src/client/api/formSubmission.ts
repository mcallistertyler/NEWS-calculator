import { NewsMeasurementData, NewsMeasurementResponse } from "../types/measurement";

export const submitFormRequest = async (measurementData: NewsMeasurementData): Promise<NewsMeasurementResponse> => {
    try {
        const response = await fetch(`/news`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(measurementData),
        });

        if (!response.ok) {
            throw new Error(`HTTP error ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        throw error;
    }
};

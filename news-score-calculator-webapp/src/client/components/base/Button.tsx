import React from 'react';

interface ButtonProps {
    text: string;
    id: string;
    type: "submit" | "reset" | "button" | undefined;
    className: string;
    onClick?: (event: React.MouseEvent<HTMLButtonElement>) => void;
}

function Button({
                    text,
                    id,
                    className,
                    type,
                    onClick
                }: ButtonProps) {

    return (
        <button
            id={id}
            className={className}
            onClick={onClick}
            type={type}
        >
            {text}
        </button>
    );
}

export default Button;

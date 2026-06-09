import { useState } from "react";
import api from "../api/documentApi";

function UploadDocument() {

    const [file, setFile] =
        useState<File | null>(null);

    const [message, setMessage] =
        useState("");

    const [documentId, setDocumentId] =
        useState("");

    const handleUpload = async () => {

        if (!file) {

            alert("Please select a file");

            return;
        }

        const formData =
            new FormData();

        formData.append(
            "file",
            file
        );

        try {

            const response =
                await api.post(
                    "/api/documents/upload",
                    formData,
                    {
                        headers: {
                            "Content-Type":
                                "multipart/form-data"
                        }
                    }
                );

            setDocumentId(
                response.data.documentId
            );

            setMessage(
                response.data.message
            );

        } catch (error) {

            console.error(error);

            setMessage(
                "Upload failed"
            );
        }
    };

    return (

        <div>

            <h2>
                Upload Document
            </h2>

            <input
                type="file"
                accept=".pdf"
                onChange={(event) =>
                    setFile(
                        event.target.files?.[0]
                            || null
                    )
                }
            />

            <br />
            <br />

            <button
                onClick={handleUpload}
            >
                Upload
            </button>

            <p>{message}</p>

            {
                documentId &&
                (
                    <p>
                        Document ID:
                        {" "}
                        {documentId}
                    </p>
                )
            }

        </div>
    );
}

export default UploadDocument;
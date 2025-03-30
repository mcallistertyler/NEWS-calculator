/// <reference types="vitest/config" />
import { defineConfig } from "vite";
import tailwindcss from '@tailwindcss/vite'
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()],
  test: {
      setupFiles: ['./test/setup.ts'],
      css: true,
      browser: {
          enabled: true,
          provider: 'playwright',
          instances: [
              { browser: 'chromium' }
          ]
      },
      typecheck: {
          tsconfig: './tsconfig.test.json'
      }
  }
});
